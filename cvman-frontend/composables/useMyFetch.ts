export const useMyFetch: typeof useFetch = (request, opts?) => {
  const config = useRuntimeConfig()

  return useFetch(request, { baseURL: "http://localhost:8080/", ...opts })
}
