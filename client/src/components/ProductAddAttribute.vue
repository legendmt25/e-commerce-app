<template>
  <template class="flex flex-col gap-1 w-full">
    <input
      type="text"
      class="outline-none border rounded p-1 transition-all shadow-sm hover:shadow hover:border-gray-400 focus:border-gray-400"
      id="title"
      name="title"
      placeholder="Title"
      v-model="title"
    />
    <div class="flex flex-wrap gap-1 items-center">
      <span>Values: </span>
      <button
        class="flex flex-row rounded-full px-5 py-2 shadow-sm text-sm bg-blue-400 text-white hover:bg-blue-500 group transition-all gap-5"
        v-for="(value, index) in values"
        @click.prevent="values.delete(value)"
        :key="index"
      >
        <span class="flex-grow break-all">{{ value.value }}</span
        ><span class="group-hover:text-black">X</span>
      </button>
      <div class="flex gap-2" @keydown.enter.prevent="handleAddValue">
        <input
          class="border-b outline-none shadow-sm hover:border-b-gray-400 focus:border-gray-400 transition-all"
          :class="{ 'w-full p-1 px-2': showAddValue, 'w-0': !showAddValue }"
          type="text"
          name="value"
          v-model="value"
        />
        <button
          type="button"
          @click="handleAddValue"
          class="rounded-full shadow hover:shadow-xl p-3 bg-blue-600 hover:bg-blue-500 text-white text-sm font-bold whitespace-nowrap"
        >
          Add value
        </button>
      </div>
    </div>
  </template>
</template>

<script>
import { computed } from "vue";

export default {
  props: {
    modelValue: {
      type: Object,
      default: () => ({ title: "", values: new Set() }),
      required: true,
    },
  },
  name: "productAddAttribute",
  data() {
    return {
      showAddValue: false,
      value: "",
    };
  },
  setup(props, { emit }) {
    const title = computed({
      get: () => props.modelValue.title,
      set: (title) => emit("update:modelValue", { ...props.modelValue, title }),
    });

    const values = computed({
      get: () => props.modelValue.values,
      set: (values) =>
        emit("update:modelValue", { ...props.modelValue, values }),
    });

    return {
      title,
      values,
    };
  },

  methods: {
    mouseDownButton(event) {
      event.target.classList.add("text-black");
    },
    mouseUpButton(event) {
      event.target.classList.remove("text-black");
    },
    handleAddValue() {
      if (!this.showAddValue) {
        this.showAddValue = true;
        return;
      }
      if (this.value == "") return;
      this.values.add({ value: this.value });
      this.value = "";
      this.showAddValue = false;
    },
  },
};
</script>
