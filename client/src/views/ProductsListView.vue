<template>
  <div
    class="flex flex-col gap-5 w-full md:w-3/4 mx-auto px-5 bg-slate-200/25 h-full p-2 pt-8"
  >
    <div class="flex items-center justify-between gap-5 flex-col md:flex-row">
      <div class="w-2/3 md:w-1/4">
        <range-slider
          @anyChangesHandle="anyChanges = true"
          :min="rangeValues[0]"
          :max="rangeValues[1]"
          v-model:values="filters.price"
        ></range-slider>
      </div>
      <div class="flex gap-2">
        <select
          @change="anyChanges = true"
          v-model="filters.sortBy"
          class="p-4 border hover:border-gray-400 rounded shadow-sm transition-all bg-white"
        >
          <option
            v-for="(value, index) in sortByValues"
            :key="index"
            :value="value"
          >
            {{ value }}
          </option>
        </select>
        <select
          @change="anyChanges = true"
          v-model="filters.size"
          class="p-4 border hover:border-gray-400 rounded shadow-sm transition-all bg-white"
        >
          <option
            v-for="(value, index) in pageSizeValues"
            :key="index"
            :value="value"
          >
            {{ value }}
          </option>
        </select>
        <button
          @click="productsPreview = productsPreview == 'grid' ? 'line' : 'grid'"
        >
          {{ productsPreview }}
        </button>
      </div>
    </div>

    <a
      v-if="anyChanges"
      @click="anyChangesClickHandle"
      class="ml-auto transition-all rounded hover:text-blue-400 text-blue-500 cursor-pointer"
    >
      Apply filters
    </a>
    <hr />
    <div
      class="p-10 md:px-20 flex flex-wrap gap-3 justify-center md:justify-start"
    >
      <products-list
        :changesApplied="!anyChanges"
        @updateRangePrice="updateRangePrice"
      ></products-list>
    </div>
  </div>
</template>

<script>
import ProductsList from "../components/ProductsList.vue";
import RangeSlider from "@/components/RangeSlider.vue";
import { provide, ref } from "@vue/runtime-core";
export default {
  data() {
    return {
      pageSizeValues: [10, 25, 50, 75, 100],
      sortByValues: [
        "Standard",
        "Price (High)",
        "Price (Low)",
        "Name (A - Z)",
        "Name (Z - A)",
      ],
      anyChanges: false,
      productsPreview: "grid",
      rangeValues: [0, 1],
    };
  },
  setup() {
    const filters = ref({
      size: 10,
      sortBy: "Standard",
      price: { low: -1, high: -1 },
    });
    provide("filters", filters);
    return { filters };
  },
  components: {
    ProductsList,
    RangeSlider,
  },
  methods: {
    anyChangesClickHandle() {
      this.anyChanges = false;
    },
    updateRangePrice(min, max) {
      this.rangeValues = [min, max];
    },
  },
};
</script>
