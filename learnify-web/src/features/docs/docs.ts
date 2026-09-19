const apiUrl = (
  import.meta.env.VITE_API_URL || "http://localhost:8080"
).replace(/\/$/, "");

export const apiDocsUrl = `${apiUrl}/v1/docs`;
