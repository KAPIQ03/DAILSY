export const setAuthToken = (token: string) => {
  if (typeof window !== "undefined") {
    localStorage.setItem("jwt_token", token);
  }
};

export const getAuthToken = (): string | null => {
  if (typeof window !== "undefined") {
    return localStorage.getItem("jwt_token");
  }
  return null;
};

export const removeAuthToken = () => {
  if (typeof window !== "undefined") {
    localStorage.removeItem("jwt_token");
  }
};