<template>
  <footer
    class="flex justify-between w-full text-gray-400 text-xs p-2 bg-slate-200/25 select-none border-t"
  >
    <span>@Copyright</span>
    <span class="flex gap-2 px-2">
      <a :class="hoverClass" target="_blank" href="#">Facebook</a>
      <a :class="hoverClass" target="_blank" href="/about">Instagram</a>
      <a :class="hoverClass" target="_blank" href="/discord">Discord</a>
    </span>
  </footer>
</template>

<script>
export default {
  data() {
    return { hoverClass: "hover:text-black transition" };
  },
};
</script>
