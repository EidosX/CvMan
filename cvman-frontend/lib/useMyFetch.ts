import { AsyncData } from "nuxt/app"
import { ZodType, z } from "zod"
import type { FetchError } from "ofetch"

export async function useMyFetch<T, ErrorT = FetchError>(
  request: string,
  { schema, ...opts }: { schema: ZodType<T> } & Parameters<typeof useFetch<T, ErrorT>>[1]
): Promise<AsyncData<T, ErrorT>> {
  const result = await useFetch<T, ErrorT>(request, {
    baseURL: "http://localhost:8080/",
    ...opts
  })
  return {
    ...result,
    data: {
      ...result.data,
      value: schema.parse(result.data.value)
    }
  }
}
