<template>
  <div class="flex flex-col md:flex-row gap-3">
    <h2>{{ product.title }}</h2>
    <div class="flex flex-col gap-2">
      <div
        class="px-1 flex flex-col gap-1 w-52"
        v-for="(attribute, attr_index) in product.attributes"
        :key="attr_index"
      >
        <label>{{ attribute.title }}</label>
        <select
          @change="(event) => updateAttributes(event.target.value)"
          class="outline-none border hover:border-gray-400 rounded shadow pl-2 pr-4 py-2 transition"
        >
          <template v-for="(value, index) in attribute.values" :key="index">
            <option :value="value.id" v-if="value.show">
              {{ value.value }}
            </option>
          </template>
        </select>
      </div>
      <div class="gap-2 inline-flex items-start">
        <button
          class="text-center p-3 px-6 md:px-6 text-sm md:text-lg rounded-md bg-gradient-to-r from-green-600 to-green-700 hover:to-green-500 text-white hover:opacity-90 transition-all"
        >
          Buy now
        </button>
        <button
          class="text-center p-3 px-6 md:px-6 text-sm md:text-lg rounded-md bg-gradient-to-r from-green-700 to-green-600 hover:to-green-500 text-white hover:opacity-90 transition-all"
        >
          Add to cart
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import repository from "@/repository";
import { Graph } from "@/graph";
export default {
  data() {
    return {
      product: {
        title: "",
        attributes: [
          {
            id: 0,
            title: "",
            values: [
              {
                id: 0,
                value: "",
                show: true,
              },
            ],
          },
        ],
        connections: [],
      },
      graph: new Graph(),
    };
  },
  async created() {
    this.product = await repository.fetchProduct(this.$route.params.id);
    this.graph = Graph.createGraphForProduct(this.product);
    this.updateAttributes(Object.values(this.graph.getNodeValues())[0].id);
  },
  methods: {
    updateAttributes(start) {
      const res = this.graph.bfs(start, start);
      Object.values(this.graph.getNodeValues()).forEach((nodeValue) => {
        if (res.has(nodeValue.id)) {
          nodeValue.show = true;
        } else {
          nodeValue.show = false;
        }
      });
    },
  },
};
</script>
