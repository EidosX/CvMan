<!--
 Copyright (c) 2023 Diego Imbert
 
 This software is released under the MIT License.
 https://opensource.org/licenses/MIT
-->

<template type="text/x-template" id="users-page-template">
  <v-container class="relative h-screen flex flex-col grow-0">
    <h1 class="text-h2 pt-4 pb-8">Liste des utilisateurs</h1>
    <div class="flex flex-1 min-h-0 pb-4">
      <user-list
        :on-select-user="onSelectUser"
        :selected-user-id="selectedUserId"
      ></user-list>
      <user-details :selected-user-id="selectedUserId"></user-details>
    </div>
  </v-container>
</template>

<script>
  {
    const { ref } = Vue
    const UsersPage = {
      computed: {
        selectedUserId: ({ $route: { params } }) =>
          params.selectedUserId ? parseInt(params.selectedUserId) : null
      },
      methods: {
        onSelectUser(u) {
          this.$router.push("/users" + (u ? "/" + u.id : ""))
        }
      },
      template: "#users-page-template"
    }
    routes.push({ path: "/users", component: UsersPage })
    routes.push({ path: "/users/:selectedUserId", component: UsersPage })
  }
</script>
