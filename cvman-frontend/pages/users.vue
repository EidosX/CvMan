<template>
  <VContainer style="padding-bottom: 2.5rem">
    <h1 style="padding: 1rem 0 1.8rem 0" class="text-h2">Liste des utilisateurs</h1>
    <VRow>
      <VCol style="max-width: 22rem">
        <VTextField
          variant="filled"
          placeholder="Rechercher un utilisateur"
          prepend-inner-icon="mdi-magnify"
          clear-icon="mdi-close"
          clearable
          style="max-height: 4rem"
        />
        <VList lines="two" height="30rem">
          <VListItem
            v-for="(u, i) in users"
            link
            v-on:click="selectUser(u)"
            :active="selectedUser.status == 'ok' && selectedUser.data.id == u.id"
            :key="i"
            :title="u.firstName + u.lastName"
            :subtitle="u.shortDescription"
            :prepend-avatar="`https://randomuser.me/api/portraits/men/${i % 70}.jpg`"
            style="padding: 1rem 0.8rem"
          ></VListItem>
        </VList>
      </VCol>
      <VProgressCircular
        indeterminate
        v-if="selectedUser.status == 'loading'"
        style="margin: auto"
      />
      <VCard
        v-if="selectedUser.status == 'ok'"
        style="flex: 1; padding: 1.6rem 2rem; max-height: 36.6rem; overflow: scroll"
      >
        <h2 style="padding-bottom: 1rem" class="text-h4">
          {{ selectedUser.data.firstName }} {{ selectedUser.data.lastName }}
        </h2>
        <p>
          {{ selectedUser.data.description }}
        </p>
        <h3 style="padding: 1rem 0">Liste des CVs</h3>
        <VExpansionPanels>
          <VExpansionPanel
            v-for="i in 10"
            title="Title"
            text="Lorem ipsum dolor sit amet consectetur adipisicing elit. Commodi, ratione debitis quis est labore voluptatibus! Eaque cupiditate minima"
          />
        </VExpansionPanels>
      </VCard>
    </VRow>
  </VContainer>
</template>

<script lang="ts" setup>
import { useMyFetch } from "@/lib/useMyFetch"
import { pageableSchema } from "@/lib/pageable"
import {
  userListOutSchema,
  UserListOut,
  userDetailsOutSchema,
  UserDetailsOut
} from "@/lib/model/user/UserScalar"

let users = ref<UserListOut>([])
let currentPage = 0
let selectedUser = ref<
  { status: "ok"; data: UserDetailsOut } | { status: "loading" } | { status: "none" }
>({ status: "none" })

async function fetchNextPage() {
  const { data } = await useMyFetch("api/user/list", {
    params: { pageNumber: currentPage },
    schema: pageableSchema(userListOutSchema)
  })
  users.value.push(...data.value.content)
  console.log(users)
}

async function selectUser({ id }: { id: number }) {
  if (selectedUser.value.status == "ok" && selectedUser.value.data.id == id) {
    selectedUser.value = { status: "none" }
    return
  }
  selectedUser.value = { status: "loading" }
  const { data } = await useMyFetch(`api/user/details/${id}`, {
    schema: userDetailsOutSchema
  })
  selectedUser.value = { status: "ok", data: data.value }
}

fetchNextPage()
</script>
