import { ZodSchema, z } from "zod"

export function pageableSchema<T extends ZodSchema>(t: T) {
  return z.object({
    content: t,
    pageable: z.object({
      pageNumber: z.number(),
      pageSize: z.number(),
      sort: z.object({
        sorted: z.boolean(),
        unsorted: z.boolean(),
        empty: z.boolean()
      }),
      offset: z.number(),
      paged: z.boolean(),
      unpaged: z.boolean()
    }),
    totalPages: z.number(),
    totalElements: z.number(),
    last: z.boolean(),
    first: z.boolean(),
    sort: z.object({
      sorted: z.boolean(),
      unsorted: z.boolean(),
      empty: z.boolean()
    }),
    number: z.number(),
    numberOfElements: z.number(),
    size: z.number(),
    empty: z.boolean()
  })
}
export type Pageable<T> = z.infer<ReturnType<typeof pageableSchema<z.ZodType<T>>>>
