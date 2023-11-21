<!--
 Copyright (c) 2023 Diego Imbert
 
 This software is released under the MIT License.
 https://opensource.org/licenses/MIT
-->

<template type="text/x-template" id="home-page-template">
  <h1>Welcome home, visitor!</h1>
  <counter></counter>
</template>

<script>
  {
    const { ref } = Vue
    const HomePage = {
      setup() {
        const count = ref(0)
        return { count }
      },
      template: "#home-page-template"
    }
    routes.push({ path: "/", component: HomePage })
  }
</script>
