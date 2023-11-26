<!--
 Copyright (c) 2023 Diego Imbert
 
 This software is released under the MIT License.
 https://opensource.org/licenses/MIT
-->

<template type="text/x-template" id="edit-user-template">
  <div class="fixed z-10 bottom-6 w-screen flex gap-4 justify-center">
    <v-overlay class="align-center justify-center">
      <template #activator="{ isActive, props }">
        <v-btn v-bind="props" color="white" class="px-6">Modifier le mot de passe</v-btn>
      </template>
      <v-card class="w-96 px-6 py-6">
        <h2 class="pb-6 font-bold">Changer le mot de passe</h2>
        <v-text-field
          type="password"
          label="Mot de passe"
          v-model="password"
        ></v-text-field>
        <v-text-field
          type="password"
          label="Confirmation"
          v-model="passwordConfirmation"
        ></v-text-field>
        <v-btn @click="updatePassword">Modifier</v-btn>
      </v-card>
    </v-overlay>
    <v-btn color="blue" class="px-8" @click="onSubmit"
      >Enregistrer les modifications</v-btn
    >
  </div>
  <div class="px-6 py-4 mb-16 flex gap-6 w-full">
    <div class="shrink-0 relative" style="flex-basis: 24rem">
      <form>
        <v-text-field label="Pr&eacute;nom" v-model="details.firstName"></v-text-field>
        <v-text-field label="Nom de famille" v-model="details.lastName"></v-text-field>
        <v-textarea label="Description" v-model="details.description"></v-textarea>
        <v-text-field label="Site web" v-model="details.website"></v-text-field>
        <v-text-field label="Adresse email" v-model="details.email"></v-text-field>
      </form>
    </div>
    <div class="w-full">
      <div class="flex w-full">
        <h2 class="font-bold text-2xl pb-4">Liste des CV</h2>
        <v-btn color="purple" class="ml-auto" @click="createCv">Nouveau CV</v-btn>
      </div>
      <v-select
        label="Selectionner un CV"
        :items="details.cvs"
        v-model="selectedCv"
        item-title="name"
        item-value="id"
        hide-details="auto"
        return-object
      ></v-select>
      <br />
      <template v-if="selectedCv">
        <v-btn color="red" class="mb-6" @click="deleteCv">Supprimer le CV</v-btn>
        <form>
          <v-text-field label="Nom du CV" v-model="selectedCv.name"></v-text-field>
        </form>
        <div class="flex w-full">
          <h2 class="font-bold text-2xl pb-6">Liste des activit&eacute;s</h2>
          <v-btn color="purple" class="ml-auto" @click="createActivity"
            >Nouvelle activit&eacute;</v-btn
          >
        </div>
        <div class="flex flex-col gap-8">
          <v-card v-for="a in selectedCv.activities">
            <div class="flex gap-3">
              <v-text-field
                label="Titre de l'activit&eacute;"
                v-model="a.title"
                hide-details="auto"
              ></v-text-field>
              <v-btn color="red" class="mb-6" @click="() => deleteActivity(a)"
                >Supprimer</v-btn
              >
            </div>
            <v-select
              hide-details="auto"
              label="Type"
              :items="allActivityTypes"
              v-model="a.type"
            >
              <template v-slot:item="{ props, item }">
                <v-list-item
                  v-bind="props"
                  :title="formatActivityType(item.value)"
                ></v-list-item>
              </template>
            </v-select>
            <v-select
              hide-details="auto"
              label="Ann&eacute;e"
              :items="allActivityYears"
              v-model="a.year"
            ></v-select>
            <v-textarea
              label="Description"
              v-model="a.description"
              hide-details="auto"
            ></v-textarea>
          </v-card>
        </div>
      </template>
    </div>
  </div>
  <v-snackbar v-model="snackbarEnabled" :color="snackbarColor">
    {{ snackbarText }}
  </v-snackbar>
</template>

<script>
  {
    const { ref } = Vue
    const EditUserPage = {
      template: "#edit-user-template",
      data: props => ({
        details: {
          firstName: "",
          lastName: "",
          description: "",
          website: "",
          email: "",
          cvs: []
        },
        selectedCv: null,
        password: "",
        passwordConfirmation: "",
        snackbarText: "",
        snackbarEnabled: false,
        snackbarColor: "red",
        allActivityTypes,
        formatActivityType,
        allActivityYears: [...new Array(new Date().getFullYear() - 1950 + 1)].map(
          (x, i) => i + 1950
        )
      }),
      async created() {
        const id = this.$route.params.userId
        const detailsRes = await this.$fetch("/api/user/details/" + id)
        const details = await detailsRes.json()
        this.details = details
      },
      watch: {
        selectedCv() {
          console.log(this.selectedCv)
        }
      },
      methods: {
        async createCv() {
          const userId = this.$route.params.userId
          const res = await this.$fetch("/api/cv", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ userId })
          })
          const json = await res.json()
          this.details.cvs.push(json)
          this.selectedCv = json
        },
        async createActivity() {
          const cvId = this.selectedCv.id
          const res = await this.$fetch("/api/activity", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ cvId })
          })
          const json = await res.json()
          this.selectedCv.activities = [json, ...this.selectedCv.activities]
        },
        async deleteActivity(activity) {
          if (!confirm("Etes vous sur de vouloir supprimer cette activite?")) return
          const res = await this.$fetch("/api/activity/" + activity.id, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" }
          })
          if (res.status !== 200) {
            // HANDLE ERRORS
            const text = await res.text()
            console.error(res)
            console.error(text)
            this.snackbarText = text
            this.snackbarColor = "red"
            this.snackbarEnabled = true
            return
          }
          this.selectedCv.activities = this.selectedCv.activities.filter(
            a => a.id !== activity.id
          )
        },
        async deleteCv() {
          if (!confirm("Etes vous sur de vouloir supprimer ce CV?")) return
          const res = await this.$fetch("/api/cv/" + this.selectedCv.id, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" }
          })
          if (res.status !== 200) {
            // HANDLE ERRORS
            const text = await res.text()
            console.error(res)
            console.error(text)
            this.snackbarText = text
            this.snackbarColor = "red"
            this.snackbarEnabled = true
            return
          }

          this.details.cvs = this.details.cvs.filter(c => c.id !== this.selectedCv.id)
          this.selectedCv = null
        },
        async onSubmit() {
          const res = await this.$fetch("/api/user/edit", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(this.details)
          })
          if (res.status !== 200) {
            // HANDLE ERRORS
            const text = await res.text()
            console.error(res)
            console.error(text)
            this.snackbarText = text
            this.snackbarColor = "red"
            this.snackbarEnabled = true
            return
          }

          this.snackbarText = "Le profil a ete mis a jour"
          this.snackbarColor = "success"
          this.snackbarEnabled = true
        },
        async updatePassword() {
          if (this.password !== this.passwordConfirmation) {
            this.snackbarText = "Les mots de passes ne correspondent pas"
            this.snackbarColor = "red"
            this.snackbarEnabled = true
            throw ""
          }

          const id = this.$route.params.userId

          const res = await this.$fetch("/api/user/edit-password/" + id, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ rawPassword: this.password })
          })
          if (res.status !== 200) {
            // HANDLE ERRORS
            const text = await res.text()
            console.error(res)
            console.error(text)
            this.snackbarText = text
            this.snackbarColor = "red"
            this.snackbarEnabled = true
            return
          }

          this.snackbarText = "Le mot de passe a ete mis a jour"
          this.snackbarColor = "success"
          this.snackbarEnabled = true
        }
      }
    }
    routes.push({ path: "/edit-user/:userId", component: EditUserPage })
  }
</script>
