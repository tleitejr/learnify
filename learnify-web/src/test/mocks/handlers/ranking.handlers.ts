import { HttpResponse, http } from "msw";

export const rankingHandlers = [
  http.get(/\/ranking$/, async () => {
    return HttpResponse.json({
      content: [
        { id: "1", nomeUsuario: "Ana", nivel: 8, pontuacaoTotal: 8000 },
        { id: "2", nomeUsuario: "Bruno", nivel: 7, pontuacaoTotal: 7000 },
        { id: "3", nomeUsuario: "Caio", nivel: 6, pontuacaoTotal: 6000 },
        { id: "4", nomeUsuario: "Dani", nivel: 5, pontuacaoTotal: 5000 },
        { id: "5", nomeUsuario: "Eva", nivel: 4, pontuacaoTotal: 4000 },
      ],
      empty: false,
      first: true,
      last: true,
      number: 0,
      numberOfElements: 5,
      pageable: {
        offset: 0,
        pageNumber: 0,
        pageSize: 5,
        paged: true,
        sort: { empty: true, sorted: false, unsorted: true },
        unpaged: false,
      },
      size: 5,
      sort: { empty: true, sorted: false, unsorted: true },
      totalElements: 5,
      totalPages: 1,
    });
  }),
];
