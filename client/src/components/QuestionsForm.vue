<template>
  <div>
    <div class="flex gap-3 justify-center">
      <div
        v-for="(page, index) in pages"
        :key="index"
        :class="{
          'bg-black/40 w-[3rem]': currentPage == index,
          'border-2': currentPage != index,
        }"
        class="cursor-pointer rounded-full p-1 transition ease-in-out shadow"
        @click="currentPage = index"
      ></div>
    </div>
    <div class="relative">
      <form class="px-20 mt-10" @submit.prevent="submitHandle">
        <div
          v-if="currentPage == 0"
          class="page-1 flex flex-col justify-center gap-2 h-[20rem]"
        >
          <label for="name" class="text-sm font-bold">Enter your name:</label>
          <input
            id="name"
            name="name"
            type="text"
            placeholder="ex: Martin"
            class="p-2 w-full border-b border-b-blue-400 outline-none focus:border-b-blue-600 transition-all focus:shadow-lg"
            required
            v-model="name"
          />
          <label for="weight" class="text-sm font-bold"
            >Enter your weight:</label
          >
          <input
            id="weight"
            name="weight"
            type="text"
            placeholder="ex: 54kg"
            class="p-2 w-full border-b border-b-blue-400 outline-none focus:border-b-blue-600 transition-all focus:shadow-lg"
            v-model="weight"
          />
          <label for="tel" class="text-sm font-bold"
            >Enter phone number:
          </label>
          <input
            id="tel"
            name="tel"
            type="tel"
            placeholder="ex: 070/123-456"
            class="p-2 w-full border-b border-b-blue-400 outline-none focus:border-b-blue-600 transition-all focus:shadow-lg"
            required
            maxlength="11"
            v-model="telFormated"
          />
        </div>
        <div v-if="currentPage == 1" class="flex flex-col gap-2 justify-center">
          <label for="purpose" class="text-sm font-bold">Purpose </label>
          <div
            class="flex flex-row gap-2 items-center text-sm"
            v-for="(val, index) in purposes"
            :key="val"
          >
            <input
              type="radio"
              class="h-4"
              :value="index"
              name="purpose"
              v-model="purpose"
            />
            {{ val }}
          </div>
          <label class="text-sm font-bold"
            >When is the best time that we should call you?</label
          >
          <input
            type="datetime-local"
            v-model="dateTime"
            class="p-2 w-full border-b border-b-blue-400 outline-none focus:border-b-blue-600 transition-all focus:shadow-lg"
          />
        </div>
        <div
          v-if="currentPage == 2"
          class="flex flex-col justify-center mx-auto gap-2 w-full 2xl:w-1/2 h-[20rem]"
        >
          <span class="text-2xl border-b p-5"
            >You are one step of being part of our 21 day fit challenge</span
          >
          <button
            class="rounded p-2 px-10 w-1/2 mx-auto hover:bg-blue-400 text-sm font-bold text-slate-600 hover:text-white border border-blue-400 transition-all"
            type="submit"
          >
            Submit
          </button>
          <span class="text-sm text-slate-400"
            >- After submiting this form, we will contact you as soon as
            possible</span
          >
        </div>
      </form>
      <button
        class="text-3xl px-2 absolute left-10 top-1/2 -translate-x-1/2 -translate-y-1/2 rounded-full hover:border hover:bg-slate-200 transition shadow"
        @click="arrowClickPrevious"
        @mousedown="mouseDownButton"
        @mouseup="mouseUpButton"
      >
        &lt;
      </button>
      <button
        class="text-3xl px-2 absolute right-10 top-1/2 translate-x-1/2 -translate-y-1/2 rounded-full hover:border hover:bg-slate-200 transition shadow"
        @click="arrowClickNext"
        @mousedown="mouseDownButton"
        @mouseup="mouseUpButton"
      >
        &gt;
      </button>
    </div>
  </div>
</template>

<script>
import repository from "@/repository";
export default {
  data() {
    return {
      name: "",
      weight: "",
      tel: "",
      purpose: 0,
      dateTime: "",

      pages: new Array(3).fill("o"),
      currentPage: 0,
      purposes: [
        "Да намалам килограми",
        "Да намалам обем во струк",
        "Да зголемам мускулна маса",
        "Да го обликувам телото",
        "Да имам повеќе енергија",
        "Да имам поголема концентрација",
        "Да го зголемам имунитетот",
        "Да јадам здрав  појадок секој ден",
      ],
    };
  },
  methods: {
    mouseDownButton(event) {
      event.target.classList.remove("hover:bg-slate-200");
      event.target.classList.add("hover:bg-slate-300");
    },
    mouseUpButton(event) {
      event.target.classList.remove("hover:bg-slate-300");
      event.target.classList.add("hover:bg-slate-200");
    },
    arrowClickPrevious() {
      if (this.currentPage > 0) {
        this.currentPage--;
      }
    },
    arrowClickNext() {
      if (this.currentPage < this.pages.length - 1) {
        this.currentPage++;
      }
    },
    submitHandle(event) {
      repository
        .sendTwentyOneFitChallengeRequest({
          name: this.name,
          weight: this.weight,
          phoneNumber: this.tel,
          purpose: this.purposes[this.purpose],
          dateTime: this.dateTime,
        })
        .then((data) => {
          if (data) event.target.textContent = "Thank you";
          else event.target.textContent = "Something went wrong check console";
        });
    },
  },
  computed: {
    telFormated: {
      get: function () {
        let temp = "";
        for (let i = 0; i < this.tel.length; ++i) {
          if (i == 3) temp += "/";
          if (i == 6) temp += "-";
          temp += this.tel[i];
        }
        return temp;
      },
      set: function (value) {
        const temp = value.replaceAll(/[/-]/g, "");
        if (temp.length > 9) return;
        this.tel = temp;
      },
    },
  },
};
</script>
