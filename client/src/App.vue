<template>
  <chat-component></chat-component>
  <div class="flex flex-col h-full">
    <navigation-bar></navigation-bar>
    <div class="flex-grow">
      <router-view />
    </div>
    <footer-component></footer-component>
  </div>
</template>

<script>
import NavigationBar from "./components/NavigationBar";
import FooterComponent from "./components/FooterComponent.vue";
import ChatComponent from "./components/ChatComponent.vue";
export default {
  components: { NavigationBar, FooterComponent, ChatComponent },
};
</script>
