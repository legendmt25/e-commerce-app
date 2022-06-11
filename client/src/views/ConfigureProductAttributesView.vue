<template>
  <div
    class="flex flex-col gap-2 w-full md:w-3/4 mx-auto px-5 bg-slate-200/25 h-full items-center"
  >
    <configure-product-attributes></configure-product-attributes>
  </div>
</template>

<script>
import ConfigureProductAttributes from "@/components/ConfigureProductAttributes.vue";
export default {
  components: { ConfigureProductAttributes },
};
</script>

<style></style>
