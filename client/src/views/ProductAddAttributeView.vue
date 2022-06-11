<template>
  <div
    class="flex flex-col gap-2 items-center w-full md:w-3/4 mx-auto px-5 bg-slate-200/25 h-full"
  >
    <div>
      <button class="rounded-full p-3" @click="attributesNumber++">+</button>
      <span class="p-3 select-none font-bold text-lg">{{
        attributesNumber
      }}</span>
      <button
        class="rounded-full p-3"
        @click="attributesNumber > startNumber && attributesNumber--"
      >
        -
      </button>
    </div>
    <form
      @submit.prevent="handleSubmit"
      class="w-full items-center flex flex-wrap justify-start"
    >
      <div
        v-for="key in Object.keys(attributes)"
        :key="key"
        class="w-full md:w-1/2 xl:w-1/3 self-start"
      >
        <product-add-attribute
          class="mx-auto px-10 py-5"
          v-model="attributes[key]"
        ></product-add-attribute>
      </div>
      <input
        type="submit"
        class="rounded-full shadow hover:shadow-xl flex-grow p-3 bg-blue-600 hover:bg-blue-500 text-white text-sm font-bold w-full disabled:opacity-40"
        value="Submit"
        :disabled="
          values.length == 0 ||
          values.filter((x) => x.title.toLowerCase() == 'цена').length == 0
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
export default {
  components: { ProductAddAttribute },
  data() {
    return {
      attributesNumber: 0,
      startNumber: 0,
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
  },
  async created() {
    const attrs = await repository.getProductAttributes(this.$route.params.id);
    attrs.forEach(
      (attr) =>
        (this.attributes[attr.id] = { ...attr, values: new Set(attr.values) })
    );
    this.startNumber = attrs.length;
    this.attributesNumber = attrs.length;
  },
  methods: {
    async handleSubmit() {
      if (this.values.length == 0) return;
      await repository.addAttributes(this.$route.params.id, this.values);
      this.$router.push(
        `/products/${this.$route.params.id}/configure-attributes`
      );
    },
  },
};
</script>
