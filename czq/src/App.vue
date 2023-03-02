<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import UserManger from "@/views/UserManger.vue";

</script>

<template>

  <div class="app_div">
    <router-view/>
  </div>
</template>

<style scoped>
  .app_div {
    width: 100%;
    height: 100%;
  }
</style>
