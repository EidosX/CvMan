<!--
 Copyright (c) 2023 Diego Imbert
 
 This software is released under the MIT License.
 https://opensource.org/licenses/MIT
-->
<!--
 Copyright (c) 2023 Diego Imbert
 
 This software is released under the MIT License.
 https://opensource.org/licenses/MIT
-->

<template type="text/x-template" id="user-details-template">
  <span style="flex: 0 0 65%">
    <span v-if="selectedUserId && details"> </span>
    <span v-else-if="selectedUserId && !details"> </span>
    <span v-else>Aucun utilisateur selectionn&eacute</span>
  </span>
</template>

<script>
  {
    const { toRef } = Vue
    app.component("user-details", {
      props: {
        selectedUserId: Number
      },
      data: props => ({
        details: null,
        _selectedUserId: toRef(props, "selectedUserId")
      }),
      template: "#user-details-template"
    })
  }
</script>
