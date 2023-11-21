<!--
 Copyright (c) 2023 Diego Imbert
 
 This software is released under the MIT License.
 https://opensource.org/licenses/MIT
-->
<!--
 Copyright (c) 2023 Diego Imbert
 
 This software is released under the MIT License.
 https://opensource.org/licenses/MIT
-->

<template type="text/x-template" id="user-details-template">
  <v-card style="flex: 1 0 65%" class="px-8 py-6 overflow-y-auto">
    <div v-if="selectedUserId && details">
      <div class="flex pb-4 gap-6 items-center">
        <v-avatar
          :image="details.avatar || 'https://gravatar.com/avatar'"
          size="90"
        ></v-avatar>
        <div>
          <h2 style="padding-bottom: 1rem leading-0" class="text-h4">
            {{ details.firstName }} {{ details.lastName }}
          </h2>
          <div class="flex gap-2">
            <v-icon icon="mdi-party-popper"></v-icon>
            <p>{{ details.age }} ans</p>
          </div>
        </div>
        <div class="ml-auto">
          <p class="text-sm flex gap-1 justify-end" v-if="details.website">
            <a :href="details.website" class="text-blue-500">
              {{ details.website }}
            </a>
            <v-icon icon="mdi-web"></v-icon>
          </p>
          <p class="text-sm flex gap-1 justify-end" v-if="details.email">
            <a :href="'mailto:' + details.email" class="text-blue-500">
              {{ details.email }}
            </a>
            <v-icon icon="mdi-email"></v-icon>
          </p>
        </div>
      </div>
      <p>
        {{ details.description }}
      </p>
      <template v-if="details.cvs.length">
        <v-expansion-panels style="padding: 1rem 0 0 0">
          <v-expansion-panel v-for="cv in details.cvs" :title="cv.name">
            <v-expansion-panel-text v-if="!cv.activities.length">
              <p style="color: grey">Aucune activit&eacute dans ce CV...</p>
            </v-expansion-panel-text>
            <v-expansion-panel-text v-for="a in cv.activities">
              <p>
                <span style="font-weight: bold">{{ a.title }}</span>
                <span style="color: grey"> ({{ a.year }})</span>
              </p>
              <p style="color: chocolate; font-style: italic">
                {{ formatActivityType(a.type) }}
              </p>
              <p style="color: grey">{{ a.description }}</p>
            </v-expansion-panel-text>
          </v-expansion-panel>
        </v-expansion-panels>
      </template>
      <template v-else>
        <h3 style="padding: 1rem 0" class="text-button">Aucun CV pour l'instant...</h3>
      </template>
    </div>
    <div
      v-else-if="selectedUserId && !details"
      class="w-full h-full items-center justify-center flex"
    >
      <v-progress-circular indeterminate></v-progress-circular>
    </div>
    <div v-else class="w-full h-full items-center justify-center flex">
      <p class="text-slate-400 text-2xl font-light">
        Aucun utilisateur selectionn&eacute
      </p>
    </div>
  </v-card>
</template>

<script>
  {
    const { toRef } = Vue
    app.component("user-details", {
      props: {
        selectedUserId: Number
      },
      data: props => ({
        details: null,
        _selectedUserId: toRef(props, "selectedUserId")
      }),
      methods: {
        async fetchDetails() {
          const fields = ["selectedUserId"]
          const copy = copyFields(this, fields)
          this.details = null

          const details = copy.selectedUserId
            ? await fetch("/api/user/details/" + copy.selectedUserId).then(x => x.json())
            : null

          // Simulate delay
          await new Promise(resolve => setTimeout(resolve, 500))

          if (!fieldsEquals(this, copy, fields)) return
          this.details = details
        },
        formatActivityType
      },
      watch: {
        selectedUserId() {
          this.fetchDetails()
        }
      },
      created() {
        this.fetchDetails()
      },
      template: "#user-details-template"
    })
  }
</script>
