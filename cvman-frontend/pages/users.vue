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
            v-for="i in 10"
            link
            :key="i"
            :title="'Item ' + i"
            subtitle="Lorem ipsum dolor sit amet consectetur adipisicing elit"
            :prepend-avatar="`https://randomuser.me/api/portraits/men/${i % 70}.jpg`"
            style="padding: 1rem 0.8rem"
          ></VListItem>
        </VList>
      </VCol>
      <VCard style="flex: 1; padding: 1.6rem 2rem; max-height: 36.6rem; overflow: scroll">
        <h2 style="padding-bottom: 1rem" class="text-h4">Diego Imbert</h2>
        <p>
          Lorem ipsum, dolor sit amet consectetur adipisicing elit. Nesciunt, asperiores
          facere illum atque nam minima dolorum ducimus laborum possimus quis aliquam
          tenetur nemo fugiat ipsum, ratione consequatur, nostrum quam fugit? Enim harum
          obcaecati laborum ut eius. Nostrum dolorum eaque, laborum sequi soluta unde quas
          ipsam laudantium, rem culpa, ad pariatur?
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
import { useMyFetch } from "@/composables/useMyFetch"
let users = []
let currentPage = 0
const fetchedUsers = await useMyFetch("api/user/list", {
  params: { pageNumber: currentPage }
})
console.log("USERS", fetchedUsers.data)
</script>
