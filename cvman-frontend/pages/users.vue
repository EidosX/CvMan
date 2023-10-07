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
            :title="`${u.firstName} ${u.lastName}`"
            :subtitle="u.shortDescription"
            :prepend-avatar="
              u.avatar || `https://randomuser.me/api/portraits/men/${i % 70}.jpg`
            "
            style="padding: 1rem 0.8rem"
          ></VListItem>
        </VList>
      </VCol>
      <VProgressCircular
        v-if="selectedUser.status == 'loading'"
        indeterminate
        style="margin: auto"
      />
      <VCard
        v-if="selectedUser.status == 'ok'"
        :key="selectedUser.data.id"
        style="flex: 1; padding: 1.6rem 2rem; max-height: 36.6rem; overflow: scroll"
      >
        <h2 style="padding-bottom: 1rem" class="text-h4">
          {{ selectedUser.data.firstName }} {{ selectedUser.data.lastName }}
        </h2>
        <p>
          {{ selectedUser.data.description }}
        </p>
        <template v-if="selectedUser.data.cvs.length">
          <h3 style="padding: 1rem 0">Liste des CVs</h3>
          <VExpansionPanels>
            <VExpansionPanel v-for="cv in selectedUser.data.cvs" :title="cv.name">
              <VExpansionPanelText v-if="!cv.activities.length">
                <p style="color: grey">Aucune activité dans ce CV...</p>
              </VExpansionPanelText>
              <VExpansionPanelText v-for="a in cv.activities">
                <p>
                  <span style="font-weight: bold">{{ a.title }}</span>
                  <span style="color: grey"> ({{ a.year }})</span>
                </p>
                <p style="color: chocolate; font-style: italic">
                  {{ formatActivityType(a.type) }}
                </p>
                <p style="color: grey">{{ a.description }}</p>
              </VExpansionPanelText>
            </VExpansionPanel>
          </VExpansionPanels>
        </template>
        <template v-else>
          <h3 style="padding: 1rem 0" class="text-button">Aucun CV pour l'instant...</h3>
        </template>
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
import { formatActivityType } from "@/lib/model/activity/activityScalar"

let users = ref<UserListOut>([])
let currentPage = 0
let selectedUser = ref<
  | { status: "ok"; data: UserDetailsOut }
  | { status: "loading"; data: { id: number } }
  | { status: "none" }
>({ status: "none" })

async function fetchNextPage() {
  const { data } = await useMyFetch("api/user/list", {
    params: { pageNumber: currentPage },
    schema: pageableSchema(userListOutSchema)
  })
  users.value.push(...data.value.content)
  currentPage += 1
}

async function selectUser({ id }: { id: number }) {
  if (selectedUser.value.status == "ok" && selectedUser.value.data.id == id) {
    selectedUser.value = { status: "none" }
    return
  }
  selectedUser.value = { status: "loading", data: { id } }

  const { data } = await useMyFetch(`api/user/details/${id}`, {
    schema: userDetailsOutSchema
  })
  // We check for asynchronicity issues
  if (selectedUser.value.data.id == id)
    selectedUser.value = { status: "ok", data: data.value }
}

fetchNextPage()
</script>
