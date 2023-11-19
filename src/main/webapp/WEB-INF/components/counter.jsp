<!--
 Copyright (c) 2023 Diego Imbert
 
 This software is released under the MIT License.
 https://opensource.org/licenses/MIT
-->

<script>
  const { ref } = Vue
  app.component("Counter", {
    setup() {
      const count = ref(0)
      return { count }
    },
    template: `
      <button @click="count++">
        You clicked me {{ count }} times.
      </button>`
  })
</script>
