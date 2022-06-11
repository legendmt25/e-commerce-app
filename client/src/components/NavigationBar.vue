<template>
  <nav
    class="flex md:flex-row flex-col justify-between md:justify-start text-sm text-center align-middle shadow p-2 gap-1 bg-slate-200/25"
  >
    <div class="flex justify-between">
      <router-link to="/" class="select-none">
        <img
          alt="logo"
          src="../imgs/logo.png"
          class="self-center px-3 md:px-10 mx-auto h-[3rem] object-contain"
        />
      </router-link>
      <button class="md:hidden" @click="showMenu = !showMenu">=</button>
    </div>
    <div
      class="md:flex flex-col md:flex-row flex-wrap"
      :class="{ hidden: !showMenu, flex: showMenu }"
    >
      <router-link
        :class="linkStyle"
        class="px-6 py-3"
        :active-class="linkActive"
        @click="handleLinkClick"
        to="/"
      >
        Home
      </router-link>
      <router-link
        :class="linkStyle"
        class="px-6 py-3"
        :active-class="linkActive"
        @click="handleLinkClick"
        to="/products"
      >
        Products
      </router-link>
      <router-link
        :class="linkStyle"
        class="px-6 py-3"
        :active-class="linkActive"
        @click="handleLinkClick"
        to="/about"
      >
        About
      </router-link>
      <router-link
        :class="linkStyle"
        class="px-6 py-3"
        :active-class="linkActive"
        @click="handleLinkClick"
        to="/contact"
      >
        Contact
      </router-link>
      <div class="cursor-pointer relative flex items-center">
        <span :class="linkStyle" class="px-6 py-3">Challenges</span>
          <router-link
            :class="linkStyle"
            class="px-6 py-3 w-full"
            :active-class="linkActive"
            @click="handleLinkClick"
            to="/21fit"
          >
            21Fit
          </router-link>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  data() {
    return { showMenu: false };
  },
  computed: {
    linkStyle() {
      return "hover:bg-slate-300/60 transition rounded-lg";
    },
    linkActive() {
      return "bg-slate-200";
    },
  },
  methods: {
    handleLinkClick() {
      this.showMenu = false;
    },
  },
};
</script>
