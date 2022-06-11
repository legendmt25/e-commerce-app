<template>
  <div
    class="flex flex-col items-center gap-2 w-full md:w-3/4 mx-auto px-5 bg-slate-200/25 h-full justify-center"
  >
    <product-create></product-create>
  </div>
</template>

<script>
import ProductCreate from "@/components/ProductCreate.vue";
export default {
  components: { ProductCreate },
};
</script>
