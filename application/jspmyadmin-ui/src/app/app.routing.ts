import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { AuthGuard } from './helpers';
import { DatabaseListComponent, InfoComponent, StatusComponent, CharsetsComponent, VariablesComponent } from './pages/server';

const routes: Routes = [
    { path: '', component: InfoComponent, canActivate: [AuthGuard] },
    { path: 'server/databases', component: DatabaseListComponent, canActivate: [AuthGuard] },
    { path: 'server/status', component: StatusComponent, canActivate: [AuthGuard] },
    { path: 'server/charsets', component: CharsetsComponent, canActivate: [AuthGuard] },
    { path: 'server/variables', component: VariablesComponent, canActivate: [AuthGuard] },
    { path: '**', redirectTo: '', pathMatch: 'full' },
];

export const routingModule: ModuleWithProviders = RouterModule.forRoot(routes, { useHash: false });
