<!--
 Copyright (c) 2023 Diego Imbert
 
 This software is released under the MIT License.
 https://opensource.org/licenses/MIT
-->

<!DOCTYPE html>
<html lang="fr">
  <head>
    <link
      href="https://cdn.jsdelivr.net/npm/@mdi/font@5.x/css/materialdesignicons.min.css"
      rel="stylesheet"
    />
    <link href="/framework/vuetify.min.css" rel="stylesheet" />
    <script src="/framework/tailwind.js"></script>
    <script src="/framework/vue.global.js"></script>
    <script src="/framework/vuetify.min.js"></script>
    <script src="/framework/vue-router.global.js"></script>
    <title>CV Man</title>
  </head>
  <body>
    <div id="app"></div>
    <template type="text/x-template" id="app-template">
      <v-app>
        <router-view></router-view>
      </v-app>
    </template>
    <script>
      const routes = []
    </script>
    <!-- Include page templates here -->
    <%@ include file="/WEB-INF/pages/home.jsp"%>
  </body>
  <script>
    const { createApp } = Vue
    const { createVuetify } = Vuetify

    const vuetify = createVuetify()
    const router = VueRouter.createRouter({
      history: VueRouter.createWebHashHistory(),
      routes: [
        ...routes,
        {
          path: "/:pathMatch(.*)*",
          component: { template: "<div>Page Not Found</div>" }
        }
      ]
    })
    const app = createApp({ template: "#app-template" })
    app.use(vuetify)
    app.use(router)
  </script>

  <!-- Include components here -->
  <%@ include file="/WEB-INF/components/counter.jsp"%>

  <script>
    app.mount("#app")
  </script>
</html>
