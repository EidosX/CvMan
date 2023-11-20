<!--
 Copyright (c) 2023 Diego Imbert
 
 This software is released under the MIT License.
 https://opensource.org/licenses/MIT
-->

<template type="text/x-template" id="users-page-template">
  <v-container style="padding-bottom: 2.5rem">
    <h1 class="text-h2 pt-4 pb-8">Liste des utilisateurs</h1>
    <v-row>
      <user-list></user-list>
    </v-row>
  </v-container>
</template>

<script>
  {
    const UsersPage = {
      setup() {
        return {}
      },
      template: "#users-page-template"
    }
    routes.push({ path: "/users", component: UsersPage })
  }
</script>
