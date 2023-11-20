<!--
 Copyright (c) 2023 Diego Imbert
 
 This software is released under the MIT License.
 https://opensource.org/licenses/MIT
-->

<template type="text/x-template" id="users-page-template">
  <v-container style="padding-bottom: 2.5rem">
    <h1 class="text-h2 pt-4 pb-8">Liste des utilisateurs</h1>
    <v-row>
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
          @load="fetchNextPage"
          empty-text="Fin de la liste"
        >
          <v-list-item
            v-for="(u, i) in users"
            link
            v-on:click="selectUser(u)"
            :active="selectedUser.status == 'ok' && selectedUser.data.id == u.id"
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
    </v-row>
  </v-container>
</template>

<script>
  {
    const { ref, watch } = Vue
    const UsersPage = {
      setup: () => {
        const state = {
          searchbar: ref(""),
          currentPage: ref(0),
          users: ref([]),
          selectedUser: { status: "none" }
        }

        const methods = {
          async fetchNextPage({ done }) {
            const searchbar = state.searchbar.value
            const encodedSearchbar = encodeURIComponent(searchbar)
            const currentPage = state.currentPage.value
            try {
              const res = await fetch(
                "/api/user/list?pageNumber=" +
                  currentPage +
                  "&searchBar=" +
                  encodedSearchbar
              ).then(x => x.json())

              if (
                searchbar === state.searchbar.value &&
                currentPage === state.currentPage.value
              ) {
                state.currentPage.value += 1
                state.users.value.push(...res.content)
              }

              if (res.last) return done("empty")
              return done("ok")
            } catch (e) {
              console.log(e)
              return done("error")
            }
          },
          async selectUser(u) {
            console.log(u)
          }
        }

        watch(state.searchbar, newSearchbar => {
          state.currentPage.value = 0
          state.users.value = []
        })

        return { ...state, ...methods }
      },
      template: "#users-page-template"
    }
    routes.push({ path: "/users", component: UsersPage })
  }
</script>
