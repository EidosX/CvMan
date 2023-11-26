<!--
 Copyright (c) 2023 Diego Imbert
 
 This software is released under the MIT License.
 https://opensource.org/licenses/MIT
-->

<template type="text/x-template" id="edit-user-template">
  user edit ....................
</template>

<script>
  {
    const { ref } = Vue
    const EditUserPage = {
      template: "#edit-user-template"
    }
    routes.push({ path: "/edit-user/:userId", component: EditUserPage })
  }
</script>
