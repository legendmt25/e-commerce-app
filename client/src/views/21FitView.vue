<template>
  <div
    class="flex flex-col items-center gap-2 w-full md:w-3/4 mx-auto px-5 bg-slate-200/25"
  >
    <div class="w-full 2xl:w-2/3">
      <img
        src="../imgs/img1.jpg"
        alt="Image: Results come over time, not overnight"
        class="rounded-b shadow-sm pointer-events-none"
      />
      <div class="text-justify p-5">
        <h2 class="text-xl">WHAT IS A CHALLENGE FOR 21 DAYS FIT</h2>
        <span>
          A project we work on as a team with our successful colleagues and
          which allows our clients to learn how to properly correct them
          Schedule meals throughout the day to give them full support system of
          events and in a fun and simple way to achieve their purpose.
        </span>
      </div>
      <div class="self-start p-5">
        <h2 class="text-xl">WHAT DO YOU GET</h2>
        <ul class="list-disc flex flex-col gap-2">
          <li class="border-b p-2">План и програма за исхрана</li>
          <li class="border-b p-2">
            Човек ментор кој ќе се погрижи за да направите резултат
          </li>
          <li class="border-b p-2">Онлине вежбање на „ZOOM CALL“</li>
          <li class="border-b p-2">Едукација за правилна исхрана</li>
          <li class="border-b p-2">Тимска подршка и пратење на резултатот</li>
          <li class="border-b p-2">НАГРАДА 🎁🎁🎁 за најдобриот резултат</li>
        </ul>
      </div>
      <questions-form class="my-5 w-full"></questions-form>
      <span class="text-sm text-slate-400">Participants: {{ 0 }}</span>
    </div>
  </div>
</template>

<script>
import QuestionsForm from "@/components/QuestionsForm.vue";
export default {
  components: { QuestionsForm },
};
</script>
