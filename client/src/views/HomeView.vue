<template>
  <div class="flex flex-col gap-2 w-full md:w-3/4 mx-auto px-5 bg-slate-200/25">
    <div  class="w-full 2xl:w-2/3 mx-auto">
      <div class="shadow-sm p-2 text-sm md:text-base">
        <div class="flex flex-row gap-2 justify-evenly">
          <card-component></card-component>
        </div>
      </div>

      <div class="p-2 bg-">
        <h2 class="text-xl">Video:</h2>
        <div class="content flex justify-center p-5">
          <iframe
            class="shadow-sm w-full h-[24rem]"
            src="https://www.youtube.com/embed/bt5C62_6LhE"
            title="YouTube video player"
            frameborder="0"
            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
            allowfullscreen
          ></iframe>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CardComponent from "@/components/CardComponent.vue";
export default { components: { CardComponent } };
</script>
