<template>
  <div
    class="flex flex-col gap-2 items-center w-full md:w-3/4 mx-auto px-5 bg-slate-200/25 h-full mt-2"
  >
    <product-manage-attributes></product-manage-attributes>
  </div>
</template>

<script>
import ProductManageAttributes from "@/components/ProductManageAttributes.vue";
export default {
  components: { ProductManageAttributes },
};
</script>
