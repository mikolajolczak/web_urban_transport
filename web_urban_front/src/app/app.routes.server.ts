import {RenderMode, ServerRoute} from '@angular/ssr';

export const serverRoutes: ServerRoute[] = [
  {
    path: 'error/:code',
    renderMode: RenderMode.Prerender,
    getPrerenderParams: async () => {
      return [
        { code: '404' },
        { code: '403' },
        { code: '500' }
      ];
    }
  },
  {
    path: '**',
    renderMode: RenderMode.Prerender
  }
];