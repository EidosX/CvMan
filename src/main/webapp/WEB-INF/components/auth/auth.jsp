<!--
 Copyright (c) 2023 Diego Imbert
 
 This software is released under the MIT License.
 https://opensource.org/licenses/MIT
-->

<template type="text/x-template" id="auth-template">
  <v-overlay v-model="overlay" class="align-center justify-center">
    <v-card>
      <div class="flex">
        <v-overlay v-model="datePickerOverlay" class="align-center justify-center">
          <v-date-picker
            show-adjacent-months
            v-model="birthday"
            :allowed-dates="allowedDates"
          ></v-date-picker>
        </v-overlay>
        <div class="bg-emerald-400 w-96 flex flex-col items-center py-8">
          <!-- Left panel -->
          <h2 class="uppercase tracking-wider font-bold text-white text-4xl my-auto">
            <template v-if="mode == 'login'">Se connecter</template>
            <template v-if="mode == 'signup'">S'inscrire</template>
          </h2>
          <p
            class="text-xs bottom-8 text-emerald-700 flex flex-col justify-center absolute"
          >
            <template v-if="mode == 'login'">
              Vous n'avez pas de compte?<br />
              <button class="underline text-black" @click="mode = 'signup'">
                Inscrivez vous
              </button>
            </template>
            <template v-if="mode == 'signup'">
              Vous avez d&eacute;j&agrave; un compte?<br />
              <button class="underline text-black" @click="mode = 'login'">
                Connectez vous
              </button>
            </template>
          </p>
        </div>

        <div
          class="flex flex-col items-center justify-center"
          style="width: 30rem; height: 36rem"
        >
          <div class="w-full px-8 flex flex-col gap-2">
            <!-- Right panel -->
            <div v-if="mode == 'signup'" class="flex gap-3">
              <v-text-field
                hint="Entrez votre pr&eacute;nom"
                label="Pr&eacute;nom"
                variant="outlined"
                v-model="firstName"
              ></v-text-field>
              <v-text-field
                hint="Entrez votre nom de famille"
                label="Nom"
                variant="outlined"
                v-model="lastName"
              ></v-text-field>
            </div>
            <v-text-field
              :hint="mode == 'signup' ? 'Votre adresse email sera votre identifiant pour vous connecter'
              : mode == 'login'  ? 'Entrez votre adresse email' : '' "
              label="Adresse email"
              prepend-inner-icon="mdi-email"
              variant="outlined"
              v-model="email"
            ></v-text-field>
            <v-text-field
              v-if="mode == 'signup'"
              label="Date de naissance"
              variant="outlined"
              prepend-inner-icon="mdi-cake"
              v-model="formattedBirthday"
              readonly
              @click="datePickerOverlay = true"
              @focus="datePickerOverlay = true"
            ></v-text-field>
            <v-text-field
              :hint="mode == 'signup' ? 'Choisissez un mot de passe s&eacute;curis&eacute;'
            : mode == 'login'  ? 'Entrez votre mot de passe' : '' "
              label="Mot de passe"
              prepend-inner-icon="mdi-key"
              :type="showPassword ? 'text' : 'password'"
              :append-inner-icon="showPassword ? 'mdi-eye-off' : 'mdi-eye'"
              @click:append-inner="showPassword = !showPassword"
              variant="outlined"
              v-model="password"
            ></v-text-field>
            <v-text-field
              v-if="mode == 'signup'"
              hint="Retapez votre mot de passe afin de vous assurer que vous ne vous &ecirc;tes pas tromp&eacute;"
              label="Confirmation du mot de passe"
              prepend-inner-icon="mdi-key"
              :type="showPassword ? 'text' : 'password'"
              :append-inner-icon="showPassword ? 'mdi-eye-off' : 'mdi-eye'"
              @click:append-inner="showPassword = !showPassword"
              variant="outlined"
              v-model="passwordConfirmation"
            ></v-text-field>
          </div>
          <v-btn
            append-icon="$vuetify"
            class="bottom-8 absolute"
            @click="mode == 'login' ? login() : mode == 'signup' ? signup() : 0"
          >
            <template v-if="mode == 'login'">Se connecter</template>
            <template v-if="mode == 'signup'">S'inscrire</template>
          </v-btn>
        </div>
      </div>
    </v-card>
  </v-overlay>
</template>

<script>
  {
    const { ref } = Vue
    app.component("auth", {
      props: {
        onToken: Function
      },
      data: props => ({
        overlay: false,
        datePickerOverlay: false,
        mode: "login",
        showPassword: false,
        email: "",
        firstName: "",
        lastName: "",
        birthday: null,
        password: "",
        passwordConfirmation: ""
      }),
      watch: {
        birthday() {
          this.datePickerOverlay = false
        },
        formattedBirthday() {}
      },
      computed: {
        formattedBirthday: ({ birthday }) => birthday?.toLocaleDateString()
      },
      methods: {
        open() {
          this.overlay = true
        },
        async login() {
          const { email, password } = this
          const res = await this.$fetch("/api/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
          })
          if (res.status !== 200) {
            // HANDLE ERRORS
            console.error(res)
            console.error(await res.text())
            return
          }
          const { token } = await res.json()
          this.onToken(token)
        },
        async signup() {
          const { email, firstName, lastName, password: rawPassword, birthday } = this
          const res = await this.$fetch("/api/auth/signup", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, firstName, lastName, rawPassword, birthday })
          })
          if (res.status !== 200) {
            // HANDLE ERRORS
            console.error(res)
            console.error(await res.text())
            return
          }
        },
        allowedDates(date) {
          return date < new Date()
        }
      },
      template: "#auth-template"
    })
  }
</script>
