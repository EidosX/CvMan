<!--
 Copyright (c) 2023 Diego Imbert
 
 This software is released under the MIT License.
 https://opensource.org/licenses/MIT
-->

<template type="text/x-template" id="user-list-template">
  <v-col style="max-width: 22rem">
    <v-text-field
      variant="filled"
      placeholder="Rechercher un utilisateur"
      prepend-inner-icon="mdi-magnify"
      clear-icon="mdi-close"
      v-model="searchbar"
      clearable
    ></v-text-field>
    <v-infinite-scroll
      :height="520"
      :items="users"
      :key="searchbar"
      @load="fetchNextPage"
      empty-text="Fin de la liste"
    >
      <v-list-item
        v-for="(u, i) in users"
        link
        v-on:click="selectUser(u)"
        :active="selectedUserId === u.id"
        :key="i"
        :title="u.firstName + ' ' + u.lastName"
        :subtitle="u.shortDescription"
        :prepend-avatar="
            u.avatar || `https://randomuser.me/api/portraits/men/${i % 70}.jpg`
          "
        style="padding: 1rem 0.8rem"
      ></v-list-item>
    </v-infinite-scroll>
  </v-col>
</template>

<script>
  {
    const { ref, watch, toRef } = Vue
    app.component("user-list", {
      props: {
        selectedUserId: Number,
        onSelectUser: Function
      },
      data: props => ({
        searchbar: "",
        currentPage: 0,
        users: [],
        _selectedUserId: toRef(props, "selectedUserId")
      }),
      methods: {
        async fetchNextPage({ done } = {}) {
          const searchbar = this.searchbar
          const encodedSearchbar = encodeURIComponent(searchbar ?? "")
          const currentPage = this.currentPage
          try {
            const res = await fetch(
              "/api/user/list?pageNumber=" +
                currentPage +
                "&searchBar=" +
                encodedSearchbar
            ).then(x => x.json())

            // Simulate delay
            await new Promise(resolve => setTimeout(resolve, 500))

            if (searchbar === this.searchbar && currentPage === this.currentPage) {
              this.currentPage += 1
              this.users.push(...res.content)
            }
            if (res.last) return done?.("empty")
            return done?.("ok")
          } catch (e) {
            console.log(e)
            return done?.("error")
          }
        },
        async selectUser(u) {
          this.onSelectUser?.(this.selectedUserId === u.id ? null : u)
        }
      },
      watch: {
        searchbar() {
          this.currentPage = 0
          this.users = []
          this.fetchNextPage()
        }
      },
      template: "#user-list-template"
    })
  }
</script>
