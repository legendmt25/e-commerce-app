<template>
  <div
    class="flex flex-col items-center gap-2 w-full md:w-3/4 mx-auto px-5 bg-slate-200/25 h-full"
  >
    <product-details></product-details>
  </div>
</template>

<script>
import ProductDetails from "@/components/ProductDetails.vue";
export default {
  components: {
    ProductDetails,
  },
};
</script>
