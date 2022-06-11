<template>
  <form class="flex flex-col gap-2" @submit.prevent="submitHandle">
    <div class="flex flex-col gap-2">
      <label for="title">Title: </label>
      <input
        id="title"
        class="border hover:border-gray-400 focus:border-gray-400 rounded shadow-sm px-2 py-2 w-72 transition-all outline-none"
        type="text"
        name="title"
        v-model="obj.title"
        required
      />
    </div>
    <input
      class="px-4 py-2 bg-blue-600 hover:bg-blue-500 transition-all rounded text-white cursor-pointer"
      type="submit"
      value="Submit"
    />
  </form>
</template>

<script>
import repository from "@/repository";
export default {
  data() {
    return {
      obj: {
        title: "",
      },
    };
  },
  methods: {
    async submitHandle() {
      if (this.obj.title == "") {
        return;
      }
      const product = await repository.createProduct(this.obj);
      this.$router.push(`/products/${product.id}/add-attribute`);
    },
  },
};
</script>
