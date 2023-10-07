import { z } from "zod"

export const activityTypeSchema = z.enum([
  "PROFESSIONAL_EXPERIENCE",
  "TRAINING",
  "PROJECT",
  "OTHER"
])
export type ActivityType = z.infer<typeof activityTypeSchema>

export const activitySchema = z.object({
  id: z.number(),
  description: z.string(),
  title: z.string(),
  type: activityTypeSchema,
  website: z.string().url().nullable(),
  year: z.number()
})
export type Activity = z.infer<typeof activitySchema>

export function formatActivityType(type: ActivityType): string {
  switch (type) {
    case "TRAINING":
      return "Formation"
    case "OTHER":
      return "Autre"
    case "PROFESSIONAL_EXPERIENCE":
      return "Expérience professionnelle"
    case "PROJECT":
      return "Projet"
  }
}
