<!--
 Copyright (c) 2023 Diego Imbert
 
 This software is released under the MIT License.
 https://opensource.org/licenses/MIT
-->

<template type="text/x-template" id="user-list-template">
  <div class="flex flex-col grow p-3 max-w-md">
    <h1 class="text-h4 pt-2 pb-4 font-bold">Liste des utilisateurs</h1>
    <div class="flex align-center">
      <p class="leading-0 text-slate-400 text-sm">Rechercher:</p>
      <v-radio-group inline v-model="searchBy" hide-details="auto" class="text-sm">
        <v-radio label="Utilisateur" value="user"></v-radio>
        <v-radio label="CV" value="cv"></v-radio>
        <v-radio label="Activit&eacute;" value="activity"></v-radio>
      </v-radio-group>
    </div>
    <v-text-field
      variant="filled"
      :placeholder="searchBy == 'user'     ? 'Rechercher un utilisateur'
                  : searchBy == 'cv'       ? 'Rechercher un CV'
                  : searchBy == 'activity' ? 'Rechercher une activit&eacute;'
                  : '???'"
      prepend-inner-icon="mdi-magnify"
      clear-icon="mdi-close"
      v-model="searchbar"
      clearable
      hide-details="auto"
      class="grow-0"
    ></v-text-field>
    <v-infinite-scroll
      :items="users"
      :key="searchBy + searchbar"
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
        :prepend-avatar="u.avatar || 'https://gravatar.com/avatar'"
        class="px-4 py-3"
      ></v-list-item>
    </v-infinite-scroll>
  </div>
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
        searchBy: "user",
        _selectedUserId: toRef(props, "selectedUserId")
      }),
      methods: {
        async fetchNextPage({ done } = {}) {
          const fields = ["searchbar", "currentPage", "searchBy"]
          const copy = copyFields(this, fields)
          const encodedSearchbar = encodeURIComponent(copy.searchbar ?? "")
          try {
            const res = await this.$fetch(
              "/api/user/list?pageNumber=" +
                copy.currentPage +
                "&searchBar=" +
                encodedSearchbar +
                "&searchBy=" +
                copy.searchBy
            )
            if (res.status !== 200) {
              console.error("error")
              console.error(res)
              return
            }
            const json = await res.json()
            console.log(json)

            // Simulate delay
            await new Promise(resolve => setTimeout(resolve, 500))

            done?.(json.last ? "empty" : "ok")

            if (!fieldsEquals(copy, this, fields)) return

            this.currentPage += 1
            this.users.push(...json.content)
          } catch (e) {
            console.log(e)
            done?.("error")
          }
        },
        async selectUser(u) {
          this.onSelectUser?.(this.selectedUserId === u.id ? null : u)
        },
        reset() {
          this.currentPage = 0
          this.users = []
          this.fetchNextPage()
        }
      },
      watch: {
        searchbar() {
          this.reset()
        },
        searchBy() {
          this.reset()
        }
      },
      template: "#user-list-template"
    })
  }
</script>
