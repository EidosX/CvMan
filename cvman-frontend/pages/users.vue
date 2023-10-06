<template>
  <VContainer style="padding-bottom: 2.5rem">
    <h1 style="padding: 1rem 0 1.8rem 0" class="text-h2">Liste des utilisateurs</h1>
    <VTextField
      variant="filled"
      placeholder="Rechercher un utilisateur"
      prepend-inner-icon="mdi-magnify"
      clear-icon="mdi-close"
      clearable
      style="max-height: 4rem"
    />
    <VCol style="display: grid; grid-template-columns: repeat(3, 1fr)">
      <VListItem
        v-for="i in 10"
        link
        :to="`users/${i}`"
        :key="i"
        :title="'Item ' + i"
        subtitle="Lorem ipsum dolor sit amet consectetur adipisicing elit"
        :prepend-avatar="`https://randomuser.me/api/portraits/men/${i % 70}.jpg`"
        style="padding: 1rem 0.8rem"
      ></VListItem>
    </VCol>
  </VContainer>
</template>
