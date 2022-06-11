<template>
  <div
    class="modal-container flex items-center justify-center w-full h-full fixed transition-all duration-500 z-[9999999] top-0"
    :class="{
      'hidden bg-gray-400/40': !modalOpen,
      'bg-gray-400/70': modalOpen,
    }"
    @click.self="handleCloseModal"
  >
    <div
      class="flex flex-col gap-2 modal bg-white rounded-md shadow p-2 w-3/4 md:w-1/2 lg:w-1/3 xl:w-1/4 h-80"
    >
      <div class="font-bold p-2 bg-white text-lg flex justify-between">
        <span>Modal head</span
        ><span @click="handleCloseModal" class="cursor-pointer hover:text-black"
          >x</span
        >
      </div>
      <hr />
      <div class="flex-grow p-2" @keyup.enter="saveEventHandle">
        <div class="flex flex-col gap-2">
          <label>Attribute: </label>
          <select
            v-model="attr_id"
            class="outline-none border hover:border-gray-400 rounded shadow pl-2 pr-4 py-2 transition"
          >
            <template
              v-for="(attr, index) in this.product.attributes"
              :key="index"
            >
              <option :value="attr.id">
                {{ attr.title }}
              </option>
            </template>
          </select>
        </div>
        <div class="flex flex-col gap-2">
          <label for="title">Value: </label>
          <input
            id="title"
            class="border hover:border-gray-400 focus:border-gray-400 rounded shadow-sm px-2 py-2 transition-all outline-none"
            type="text"
            name="title"
            v-model="value"
            required
          />
        </div>
      </div>
      <hr />
      <div class="self-end p-2">
        <button
          @click="saveEventHandle"
          class="rounded-xl shadow-sm hover:shadow px-10 py-2 bg-blue-600 text-white hover:bg-blue-700 active:bg-blue-600 active:text-gray-100"
        >
          Save
        </button>
      </div>
    </div>
  </div>
  <div class="flex flex-col border md:w-2/3 w-full p-2 h-full">
    <div class="demo-control-panel flex flex-row gap-2 overflow-auto p-3">
      <button
        class="border rounded-3xl px-5 py-2 hover:border-gray-400 transition-all whitespace-nowrap"
        @click="modalOpen = true"
      >
        add node
      </button>
      <button
        class="border rounded-3xl px-5 py-2 hover:border-gray-400 transition-all whitespace-nowrap"
        @click="removeNode"
      >
        remove node
      </button>
      <button
        class="border rounded-3xl px-5 py-2 hover:border-gray-400 transition-all whitespace-nowrap"
        @click="addEdge"
      >
        add edge
      </button>
      <button
        class="border rounded-3xl px-5 py-2 hover:border-gray-400 transition-all whitespace-nowrap"
        @click="removeEdge"
      >
        remove edge
      </button>
    </div>
    <v-network-graph
      v-model:selected-nodes="selectedNodes"
      v-model:selected-edges="selectedEdges"
      :nodes="nodes"
      :edges="edges"
      :configs="configs"
    ></v-network-graph>
  </div>
</template>

<script>
import repository from "@/repository";
import * as vNG from "v-network-graph";
import { reactive, ref } from "vue";
export default {
  data() {
    return {
      product: {},
      configs: vNG.defineConfigs({
        view: {
          autoPanAndZoomOnLoad: true,
        },
        node: {
          selectable: true, // up to 2 nodes
        },
        edge: {
          selectable: true,
          normal: {
            width: 3,
          },
        },
      }),
      value: "",
      attr_id: 0,
      modalOpen: false,
    };
  },
  setup() {
    const nodes = reactive({});
    const edges = reactive({});
    const selectedNodes = ref([]);
    const selectedEdges = ref([]);

    return { nodes, edges, selectedEdges, selectedNodes };
  },
  async created() {
    this.product = await repository.fetchProduct(this.$route.params.id);
    const values = this.product.attributes.flatMap((attr) => {
      attr.values.forEach((val) => (val.attr_id = attr.id));
      return attr.values;
    });
    values.forEach((value) => {
      this.nodes[value.id] = {
        name: value.value,
        value,
      };
    });
    this.product.connections.forEach((edge) => {
      const [x, y] = edge.split(",");
      if (!(`${y},${x}` in this.edges))
        this.edges[edge] = { source: x, target: y };
    });
  },
  methods: {
    async addNode() {
      const attributeValue = await repository.createAttributeValue(
        this.attr_id,
        this.value
      );
      this.nodes[attributeValue.id] = {
        name: attributeValue.value,
        value: attributeValue,
      };
    },
    async addEdge() {
      if (this.selectedNodes.length < 1) return;
      const target = this.selectedNodes[this.selectedNodes.length - 1];

      const connected = await repository.connectAttributes(
        this.selectedNodes
          .slice(0, this.selectedNodes.length - 1)
          .map((source) => ({
            source: this.nodes[source].value.id,
            target: this.nodes[target].value.id,
          }))
      );
      connected.forEach(({ source, target }) => {
        this.edges[`${source},${target}`] = { source, target };
      });
    },
    async removeEdge() {
      const removed = await repository.disconnectAttributes(
        this.selectedEdges.map((edgeId) => {
          const { source, target } = this.edges[edgeId];
          return {
            source: this.nodes[source].value.id,
            target: this.nodes[target].value.id,
          };
        })
      );
      removed.forEach(({ source, target }) => {
        delete this.edges[`${source},${target}`];
      });
    },
    async removeNode() {
      if (this.selectedNodes.length == 0) return;
      for (const nodeId of this.selectedNodes) {
        const removed = await repository.deleteAttributeValue(
          this.nodes[nodeId].value.id
        );
        if (removed) {
          delete this.nodes[nodeId];
        }
      }
    },
    handleCloseModal() {
      this.modalOpen = false;
      this.value = "";
      this.attr_id = 0;
    },
    saveEventHandle() {
      this.addNode();
      this.handleCloseModal();
    },
  },
};
</script>
