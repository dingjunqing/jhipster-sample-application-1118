import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'aie',
    data: { pageTitle: 'jhipsterSampleApplication1118App.jhipsterSampleApplication1118AIe.home.title' },
    loadChildren: () => import('./jhipsterSampleApplication1118/aie/aie.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
