import { HttpResponse, http } from "msw";

export const disciplinasHandlers = [
  http.get(/\/disciplinas$/, async () => {
    return HttpResponse.json([
      { id: "d-1", titulo: "Matemática" },
      { id: "d-2", titulo: "Física" },
    ]);
  }),
];
