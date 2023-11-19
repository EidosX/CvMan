// Copyright (c) 2023 Diego Imbert
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

const AppRouter = {
  create: () =>
    VueRouter.createRouter({
      history: VueRouter.createWebHashHistory(),
      routes: [
        { path: "/", component: { template: "<div>Home</div>" } },
        { path: "/test/", component: { template: "<div>Test</div>" } },
        { path: "/:pathMatch(.*)*", component: { template: "<div>Page Not Found</div>" } }
      ]
    })
}
