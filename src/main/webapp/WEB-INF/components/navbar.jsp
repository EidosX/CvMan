<!--
 Copyright (c) 2023 Diego Imbert
 
 This software is released under the MIT License.
 https://opensource.org/licenses/MIT
-->

<template type="text/x-template" id="navbar-template">
  <div class="w-28 bg-slate-950 h-screen text-white flex flex-col">
    <div
      v-for="(item, index) in items"
      class="aspect-square flex flex-col align-center justify-center cursor-pointer last:mt-auto"
      :class="{'bg-slate-700': active == item.id, 'opacity-40': active != item.id}"
      @click="item.onClick"
    >
      <v-icon :icon="item.icon" size="40"></v-icon>
      <p class="text-sm pt-1 select-none">{{ item.name }}</p>
    </div>
  </div>
</template>

<script>
  {
    const { ref } = Vue
    app.component("navbar", {
      props: {
        account: Object,
        onOpenAuth: Function
      },
      data: props => ({
        items: [
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
            name: props.account ? "Mon Compte" : "Se connecter",
            onClick: props.onOpenAuth
          }
        ],
        active: "userlist"
      }),
      template: "#navbar-template"
    })
  }
</script>
