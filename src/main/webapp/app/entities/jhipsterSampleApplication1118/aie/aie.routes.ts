import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import AIEResolve from './route/aie-routing-resolve.service';

const aIERoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/aie.component').then(m => m.AIEComponent),
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/aie-detail.component').then(m => m.AIEDetailComponent),
    resolve: {
      aIE: AIEResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/aie-update.component').then(m => m.AIEUpdateComponent),
    resolve: {
      aIE: AIEResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/aie-update.component').then(m => m.AIEUpdateComponent),
    resolve: {
      aIE: AIEResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default aIERoute;
