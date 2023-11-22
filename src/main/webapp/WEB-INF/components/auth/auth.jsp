<!--
 Copyright (c) 2023 Diego Imbert
 
 This software is released under the MIT License.
 https://opensource.org/licenses/MIT
-->

<template type="text/x-template" id="auth-template">
  <v-overlay v-model="overlay" class="align-center justify-center">
    <v-card>
      <div class="flex">
        <div class="bg-emerald-400 w-96 flex flex-col items-center py-8">
          <!-- Left panel -->
          <h2 class="uppercase tracking-wider font-bold text-white text-4xl my-auto">
            <template v-if="mode == 'login'">Se connecter</template>
            <template v-if="mode == 'signup'">S'inscrire</template>
          </h2>
          <p class="text-xs mt-2 text-emerald-700">
            <template v-if="mode == 'login'">
              Vous n'avez pas de compte?
              <button class="underline text-black" @click="mode = 'signup'">
                Inscrivez vous
              </button>
            </template>
            <template v-if="mode == 'signup'">
              Vous avez d&eacute;j&agrave; un compte?
              <button class="underline text-black" @click="mode = 'login'">
                Connectez vous
              </button>
            </template>
          </p>
        </div>
        <div
          class="flex flex-col items-center justify-center"
          style="width: 32rem; height: 30rem"
        >
          <div class="w-full px-8 flex flex-col gap-2">
            <!-- Right panel -->
            <v-text-field
              :hint="mode == 'signup' ? 'Votre adresse email sera votre identifiant pour vous connecter'
                   : mode == 'login'  ? 'Entrez votre adresse email' : '' "
              label="Adresse email"
              prepend-inner-icon="mdi-email"
              variant="outlined"
              v-model="email"
            ></v-text-field>
            <v-text-field
              :hint="mode == 'signup' ? 'Choisissez un mot de passe sécurisé'
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
            class="bottom-6 absolute"
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
      data: props => ({
        overlay: true,
        mode: "login",
        showPassword: false,
        email: "",
        password: "",
        passwordConfirmation: ""
      }),
      methods: {
        async login() {
          const { email, password } = this
          const res = await fetch("/api/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
          }).then(x => x.json())
          console.log(res)
        },
        async signup() {}
      },
      template: "#auth-template"
    })
  }
</script>
