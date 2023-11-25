<!--
 Copyright (c) 2023 Diego Imbert
 
 This software is released under the MIT License.
 https://opensource.org/licenses/MIT
-->

<template type="text/x-template" id="navbar-template">
  <div class="w-28 shrink-0 bg-slate-950 h-screen text-white flex flex-col">
    <div
      v-for="(item, index) in items"
      class="aspect-square flex flex-col align-center justify-center cursor-pointer last:mt-auto"
      :class="{'bg-slate-700': active == item.id, 'opacity-40': active != item.id}"
      :id="'navbar-item-' + item.id"
      @click="item.onClick"
    >
      <v-icon :icon="item.icon" size="40"></v-icon>
      <p class="text-sm pt-1 select-none">{{ item.name }}</p>
    </div>

    <v-menu v-if="$user.value" activator="#navbar-item-account">
      <v-list>
        <v-list-item class="cursor-pointer hover:bg-slate-200" @click="onEditUser">
          <v-list-item-title>Modifier le profil</v-list-item-title>
        </v-list-item>
        <v-list-item class="cursor-pointer hover:bg-slate-200" @click="onLogout">
          <v-list-item-title>Se d&eacute;connecter</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
  </div>
</template>

<script>
  {
    const { ref } = Vue
    app.component("navbar", {
      props: {
        onOpenAuth: Function,
        onLogout: Function,
        onEditUser: Function
      },
      data: props => ({
        active: "userlist"
      }),
      computed: {
        items: ({ $user, onOpenAuth }) => [
          {
            id: "home",
            icon: "mdi-home",
            name: "Accueil",
            onClick() {}
          },
          {
            id: "userlist",
            icon: "mdi-file-account",
            name: "Utilisateurs",
            onClick() {}
          },
          {
            id: "account",
            icon: "mdi-account",
            name: $user.value ? "Mon Compte" : "Se connecter",
            onClick: $user.value ? null : onOpenAuth
          }
        ]
      },
      template: "#navbar-template"
    })
  }
</script>
