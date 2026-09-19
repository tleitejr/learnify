export interface Usuario {
  id: string;
  nome: string;
  email: string;
  nivel: number;
  pontuacaoTotal: number;
}

export interface Conquista {
  id: string;
  titulo: string;
  descricao: string;
}

export interface Ranking {
  id: string;
  nomeUsuario: string;
  nivel: number;
  pontuacaoTotal: number;
}

export interface Disciplina {
  id: string;
  titulo: string;
}

export interface Conteudo {
  id: string;
  titulo: string;
  concluido: boolean;
  ativo: boolean;
  dataCriacao: Date;
}

export interface ConteudosPageProps {
  disciplinaId?: string;
  isModal?: boolean;
}

export interface Pageable {
  content: Ranking[];
  empty: boolean;
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  pageable: {
    offset: number;
    pageNumber: number;
    pageSize: number;
    paged: boolean;
    sort: {
      empty: boolean;
      sorted: boolean;
      unsorted: boolean;
    };
    unpaged: boolean;
  };
  size: number;
  sort: {
    empty: boolean;
    sorted: boolean;
    unsorted: boolean;
  };
  totalElements: number;
  totalPages: number;
}

export interface EstatisticasUsuario {
  totalRespostas: number;
  acertos: number;
  erros: number;
  percentual: number;
  pontuacaoTotal: number;
  mediaPontos: number;
  nivel: number;
  desempenho: string;
}

export type Alternativa = {
  id: string;
  descricao: string;
};

export type Questao = {
  id: string;
  enunciado: string;
  alternativas: Alternativa[];
};

export type ResultadoQuiz = {
  correta: boolean;
  pontosGanhos: number;
  pontuacaoTotal: number;
  nivel: number;
  conquistaDesbloqueada?: string;
};

export type LogoutResponse = {
  message: string;
  dataLogout: Date;
};
