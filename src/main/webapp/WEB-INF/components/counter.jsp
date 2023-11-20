<!--
 Copyright (c) 2023 Diego Imbert
 
 This software is released under the MIT License.
 https://opensource.org/licenses/MIT
-->

<template type="text/x-template" id="counter-template">
  <button @click="count++">You clicked {{ count }} times.</button>
</template>

<script>
  {
    const { ref } = Vue
    app.component("counter", {
      setup() {
        const count = ref(0)
        return { count }
      },
      template: "#counter-template"
    })
  }
</script>
