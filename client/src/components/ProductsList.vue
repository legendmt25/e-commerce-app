<template>
  <div
    class="flex flex-col rounded hover:shadow-2xl w-64 h-72 transition-all group p-3 border bg-white"
    v-for="(product, index) in products"
    :key="index"
  >
    <div class="card-head p-2 flex flex-col">
      <span class="text-2xl">{{ product.title }}</span>
      <span class="text-sm text-gray-500">{{
        price(product.minPrice, product.maxPrice, "ден")
      }}</span>
    </div>
    <hr />
    <div class="card-body relative h-full overflow-hidden">
      <router-link
        class="px-4 bg-gradient-to-r from-blue-600 to-blue-400 text-right hover:opacity-90 transition-all rounded-tl-3xl rounded-b text-xl shadow w-full leading-relaxed h-9 translate-y-48 duration-500 text-white group-hover:translate-y-40 absolute select-none"
        :to="{ name: 'productDetails', params: { id: product.id } }"
        >Details</router-link
      >
    </div>
    <div class="px-3"></div>
  </div>
</template>

<script>
import { inject } from "@vue/runtime-core";
import repository from "../repository";
export default {
  emits: ["updateRangePrice"],
  props: {
    changesApplied: { type: Boolean },
  },
  data() {
    return {
      products: [],
    };
  },
  setup() {
    const filters = inject("filters");
    return { filters };
  },
  methods: {
    price(minPrice, maxPrice, currency) {
      if (maxPrice == minPrice) {
        return `${minPrice}${currency}`;
      }
      return `${minPrice}${currency} ~ ${maxPrice}${currency}`;
    },
  },
  async created() {
    const productsWithPrice = await repository.fetchProducts(this.filters);
    this.products = productsWithPrice.products.content;
    this.$emit(
      "updateRangePrice",
      productsWithPrice.price.minPrice,
      productsWithPrice.price.maxPrice
    );
  },
  watch: {
    changesApplied: async function () {
      if (this.changesApplied) {
        const productsWithPrice = await repository.fetchProducts(this.filters);
        this.products = productsWithPrice.products.content;
        this.$emit(
          "updateRangePrice",
          productsWithPrice.price.minPrice,
          productsWithPrice.price.maxPrice
        );
      }
    },
  },
};
</script>
