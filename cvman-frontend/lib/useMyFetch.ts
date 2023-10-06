import { ZodSchema } from "zod"

export const useMyFetch = async (
  request: Parameters<typeof useFetch>[0],
  { schema, ...opts }: Parameters<typeof useFetch>[1] & { schema?: ZodSchema } = {}
) => {
  const result = await useFetch(request, { baseURL: "http://localhost:8080/", ...opts })
  return {
    ...result,
    data: {
      ...result.data,
      value: schema?.parse(result.data.value) ?? result.data.value
    }
  }
}
