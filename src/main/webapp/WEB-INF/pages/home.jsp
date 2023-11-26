<!--
 Copyright (c) 2023 Diego Imbert
 
 This software is released under the MIT License.
 https://opensource.org/licenses/MIT
-->

<template type="text/x-template" id="home-page-template">
  <div class="flex flex-col gap-4 justify-center align-center h-full w-full">
    <h1 class="text-9xl font-black uppercase tracking-wide">CV Man</h1>
    <p>Gestionnaire de CV</p>
    <v-btn
      prepend-icon="$vuetify"
      color="blue"
      class="absolute bottom-6"
      size="x-large"
      @click="() => $root.openAuth('signup')"
    >
      Rejoindre la liste
    </v-btn>
  </div>
</template>

<script>
  {
    const { ref } = Vue
    const HomePage = {
      template: "#home-page-template"
    }
    routes.push({ path: "/", component: HomePage })
  }
</script>
