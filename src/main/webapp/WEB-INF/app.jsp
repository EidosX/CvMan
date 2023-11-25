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
        <auth ref="auth" :on-token="(t) => authToken = t"></auth>
        <div class="flex w-full">
          <navbar :on-open-auth="openAuth"></navbar>
          <router-view></router-view>
        </div>
      </v-app>
    </template>
  </body>

  <script>
    // Everything in this scope will be visible to all files
    const { routes, app } = (function init() {
      const routes = []
      const { createApp, ref, watch } = Vue
      const { createVuetify } = Vuetify

      const authTokenRef = ref(null)
      const app = createApp({
        template: "#app-template",
        data: props => ({
          authToken: authTokenRef
        }),
        methods: {
          openAuth() {
            this.$refs.auth.open()
          }
        }
      })

      app.config.globalProperties.$fetch = function (uri, params = {}) {
        return fetch(uri, {
          ...params,
          headers: {
            ...(authTokenRef.value && {
              Authorization: "Bearer " + authTokenRef.value
            }),
            ...params.headers
          }
        })
      }

      const vuetify = createVuetify()
      app.use(vuetify)

      return { routes, app }
    })()
  </script>

  <!-- Include files here -->

  <!---->
  <%@ include file="/WEB-INF/lib/util.jsp"%>
  <!---->
  <%@ include file="/WEB-INF/lib/activity.jsp"%>
  <!---->
  <%@ include file="/WEB-INF/components/navbar.jsp"%>
  <!---->
  <%@ include file="/WEB-INF/components/auth/auth.jsp"%>
  <!---->
  <%@ include file="/WEB-INF/components/counter.jsp"%>
  <!---->
  <%@ include file="/WEB-INF/components/userList.jsp"%>
  <!---->
  <%@ include file="/WEB-INF/components/userDetails.jsp"%>
  <!---->
  <%@ include file="/WEB-INF/pages/home.jsp"%>
  <!---->
  <%@ include file="/WEB-INF/pages/users.jsp"%>

  <script>
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
    app.use(router)

    app.mount("#app")
  </script>
</html>
