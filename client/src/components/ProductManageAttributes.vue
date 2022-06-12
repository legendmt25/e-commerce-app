<template>
  <div>
    <div class="flex justify-between w-full">
      <button
        class="rounded-full pt-2 pb-3 px-4 shadow-sm hover:shadow bg-blue-600 text-white hover:bg-blue-500 transition-all disabled:opacity-60"
        @click="addAttribute"
      >
        +
      </button>
      <button
        class="rounded-full pt-2 pb-3 px-4 shadow-sm hover:shadow bg-blue-600 text-white hover:bg-blue-500 transition-all disabled:opacity-60"
        @click="undoState"
        :disabled="states.length == 0"
      >
        ⎌
      </button>
    </div>
    <form
      @submit.prevent="handleSubmit"
      class="w-full items-center flex flex-wrap justify-start"
    >
      <template v-for="value in Object.values(attributes)" :key="value.id">
        <div
          v-if="value.operation != 'DELETE'"
          class="w-full md:w-1/2 xl:w-1/3 self-start"
        >
          <product-add-attribute
            @update="updateAttribute"
            @delete="deleteAttribute(value.id)"
            class="mx-auto px-10 py-5"
            v-model="attributes[value.id]"
          ></product-add-attribute>
        </div>
      </template>
      <input
        type="submit"
        class="rounded-full shadow hover:shadow-xl flex-grow p-3 bg-blue-600 hover:bg-blue-500 text-white text-sm font-bold w-full disabled:opacity-40"
        value="Submit"
        :disabled="
          values.length == 0 ||
          values.filter(
            (x) => x.title.toLowerCase() == 'цена' && x.operation != 'DELETE'
          ).length == 0
        "
        @mousedown="mouseDownButton"
        @mouseup="mouseUpButton"
      />
      <span class="text-sm text-red-700 font-bold"
        >*Attribute 'Цена' is required</span
      >
    </form>
  </div>
</template>

<script>
import ProductAddAttribute from "@/components/ProductAddAttribute.vue";
import repository from "@/repository";
import NEW_ATTRIBUTE from "@/state_command/NEW_ATTRIBUTE";
import UPDATE_ATTRIBUTE from "@/state_command/UPDATE_ATTRIBUTE";
import DELETE_ATTRIBUTE from "@/state_command/DELETE_ATTRIBUTE";
export default {
  components: { ProductAddAttribute },
  data() {
    return {
      attributesNumber: 0,
      attributes: {},
      states: [],
    };
  },
  computed: {
    values() {
      return Object.values(this.attributes)
        .filter((x) => x.title != "" && x.values.size != 0)
        .map((x) => ({ ...x, values: [...x.values] }));
    },
    deleted() {
      return Object.values(this.values).filter(
        (attr) => attr.operation == "DELETE"
      );
    },
    created() {
      return Object.values(this.values)
        .filter((value) => value.id.toString().startsWith("n"))
        .map((value) => ({ ...value, id: null }));
    },
    updated() {
      return Object.values(this.values).filter(
        (attr) => attr.operation == "UPDATE"
      );
    },
  },
  async created() {
    const attrs = await repository.getProductAttributes(this.$route.params.id);
    attrs.forEach(
      (attr) =>
        (this.attributes[attr.id] = { ...attr, values: new Set(attr.values) })
    );
  },
  methods: {
    deleteAttribute(key) {
      const cmd = new DELETE_ATTRIBUTE(key, this.attributes[key]);
      this.states.push(cmd);
      cmd.process(this.attributes);
    },
    updateAttribute(oldValue) {
      const cmd = new UPDATE_ATTRIBUTE(oldValue.id, oldValue);
      this.states.push(cmd);
      cmd.process(this.attributes);
    },
    addAttribute() {
      const key = `n${this.attributesNumber}`;
      const cmd = new NEW_ATTRIBUTE(key);
      this.states.push(cmd);
      cmd.process(this.attributes, {
        id: key,
        values: new Set(),
        title: "",
      });
      this.attributesNumber++;
      console.log(this.created);
    },
    undoState() {
      if (this.states.length == 0) return;
      this.states[this.states.length - 1].undo(this.attributes);
      this.states.pop();
    },
    async handleSubmit() {
      if (this.values.length == 0) return;
      await repository.manageAttributes(
        this.$route.params.id,
        this.deleted,
        this.updated,
        this.created
      );
      this.$router.push(
        `/products/${this.$route.params.id}/configure-attributes`
      );
    },
  },
};
</script>
